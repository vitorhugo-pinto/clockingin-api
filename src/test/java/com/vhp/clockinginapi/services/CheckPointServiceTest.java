package com.vhp.clockinginapi.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.dtos.CheckPointRequestDTO;
import com.vhp.clockinginapi.mappers.CheckPointMapper;
import com.vhp.clockinginapi.models.CheckPointEntity;
import com.vhp.clockinginapi.repositories.CheckPointRepository;
import com.vhp.clockinginapi.utils.exceptions.AlreadyHadLunchException;
import com.vhp.clockinginapi.utils.exceptions.DatePreviousThanLastRegisteredException;
import com.vhp.clockinginapi.utils.exceptions.LunchTimeBreakException;
import com.vhp.clockinginapi.utils.exceptions.UserNoLunchBreakException;

@ExtendWith(MockitoExtension.class)
public class CheckPointServiceTest {

  @InjectMocks
  private CheckPointService checkPointService;

  @Mock
  private CheckPointRepository checkPointRepository;

  @Mock
  private CheckPointMapper checkPointMapper;

  @Test
  @DisplayName("Tests that a user with job type PARTTIME cannot have a lunch break")
  public void shouldThrowUserCannotHaveLunchBreak() {
    UUID userId = UUID.randomUUID();
    String jobType = "PARTTIME";
    LocalDateTime NOW = LocalDateTime.now();

    CheckPointRequestDTO dto = new CheckPointRequestDTO(NOW, true);

    assertThrows(UserNoLunchBreakException.class, () -> {
      checkPointService.create(dto, userId, jobType);
    });
  }

  @Test
  @DisplayName("Tests if the next check point is before the last one")
  public void shouldThrowDatePreviousThatTheLastRegistered() {
    UUID userId = UUID.randomUUID();
    String jobType = "FULLTIME";
    LocalDateTime NOW = LocalDateTime.now();
    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

    CheckPointRequestDTO dto = new CheckPointRequestDTO(NOW, true);

    CheckPointEntity checkPointMock = CheckPointEntity.builder().timeStamp(END_OF_DAY).build();

    List<CheckPointEntity> userCheckPointsMock = List.of(checkPointMock);

    when(checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY)).thenReturn(userCheckPointsMock);

    assertThrows(DatePreviousThanLastRegisteredException.class, () -> {
      checkPointService.create(dto, userId, jobType);
    });
  }

  @Test
  @DisplayName("Checks is the user already had lunch in that day")
  public void shouldThrowAlreadyHadLunchu() {
    UUID userId = UUID.randomUUID();
    String jobType = "FULLTIME";
    LocalDateTime NOW = LocalDateTime.now();
    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

    CheckPointRequestDTO dto = new CheckPointRequestDTO(NOW, true);

    CheckPointEntity checkPointMock = CheckPointEntity.builder().timeStamp(START_OF_DAY).lunchBreak(true).build();

    List<CheckPointEntity> userCheckPointsMock = List.of(checkPointMock);

    when(checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY)).thenReturn(userCheckPointsMock);

    assertThrows(AlreadyHadLunchException.class, () -> {
      checkPointService.create(dto, userId, jobType);
    });
  }

  @Test
  @DisplayName("User tries to come back from lunch before 1 hour")
  public void shouldThrowLunchTimeBreakException() {
    UUID userId = UUID.randomUUID();
    String jobType = "FULLTIME";
    LocalDateTime NOW = LocalDateTime.now();
    LocalDateTime BEFORE_ONE_HOUR = NOW.plusMinutes(59);
    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

    CheckPointRequestDTO dto = new CheckPointRequestDTO(BEFORE_ONE_HOUR, false);

    CheckPointEntity checkPointMock = CheckPointEntity.builder().timeStamp(NOW).lunchBreak(true).build();

    List<CheckPointEntity> userCheckPointsMock = List.of(checkPointMock);

    when(checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY)).thenReturn(userCheckPointsMock);

    assertThrows(LunchTimeBreakException.class, () -> {
      checkPointService.create(dto, userId, jobType);
    });
  }

  @Test
  @DisplayName("User register check point after lunch")
  public void shouldRegisterCheckPointAfterLunch() {
    UUID userId = UUID.randomUUID();
    UUID id = UUID.randomUUID();
    String jobType = "FULLTIME";
    LocalDateTime NOW = LocalDateTime.now();
    LocalDateTime BEFORE_ONE_HOUR = NOW.plusMinutes(60);
    LocalDateTime START_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime END_OF_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

    CheckPointRequestDTO dto = new CheckPointRequestDTO(BEFORE_ONE_HOUR, false);

    CheckPointDTO responseDTO = new CheckPointDTO(id, null, userId, dto.timeStamp(), false);

    CheckPointEntity checkPointMock = CheckPointEntity.builder().timeStamp(NOW).lunchBreak(true).build();

    List<CheckPointEntity> userCheckPointsMock = List.of(checkPointMock);

    when(checkPointRepository.findAllByUserIdAndTimeStampBetweenOrderByTimeStampAsc(userId, START_OF_DAY, END_OF_DAY)).thenReturn(userCheckPointsMock);
    when(checkPointService.create(dto, userId, jobType)).thenReturn(responseDTO);

    var response = checkPointService.create(dto, userId, jobType);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response).isInstanceOf(responseDTO.getClass());
  }
}
