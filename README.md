# Clocking in API

![forthebadge](https://img.shields.io/badge/Spring-6DB33F.svg?style=for-the-badge&logo=Spring&logoColor=white) ![forthebadge](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white) ![forthebadge](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white) ![forthebadge](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white)

API for a clocking in system for employees to manage their working hours.

---

## Specifications

- Java: 17
- Spring Boot: 3.2.4
- Maven
- Docker: container para banco de dados.
  - Folder: docker/docker-compose.yaml
  - __WHEN RUNNING THE PROJECT FOR THE FIRST TIME, RUN THE FOLLOW SQL TO INSERT FIRST ADMIN USER:__
```sql
CREATE EXTENSION pgcrypto;

insert into users (id,created_at,job_type,login,"name","password","role")
values (gen_random_uuid(), NOW(), 'FULLTIME', 'admin', 'admin', crypt('admin', gen_salt('bf')), 'ADMIN');
```
  - This will allow to access the system with login: admin, password: admin. Using the POST feature to create new users.

### DER
The following DER represent the relations between entities, althoug JOBTYPE and ROLE could have been other TABLES, due to the small size of the project it was opted to use them as attributes.

<img width="363" alt="Screenshot 2024-04-10 at 22 04 40" src="https://github.com/vitorhugo-pinto/clockingin-api/assets/83354219/a096dc61-1b03-412e-a7c1-366415f302af">

---

## Features
-  Swagger: /swagger-ui/index.html
<img width="708" alt="Screenshot 2024-04-10 at 22 22 43" src="https://github.com/vitorhugo-pinto/clockingin-api/assets/83354219/55eb301a-85f5-4395-8be0-f5ddff793cda">


- /user -> creates an user (ADMIN role only)
- /check-point/clock-in -> registers the employee clock in time, can be lunch break or not.
- /check-point/summary -> lists all checkpoints from the user, the work balance hours for the day and if the users mets the hours for his jouney type (JOBTYPE -> FULLTIME (8hrs/day) or PARTTIME (6/hrs) (Note: PARTTIME cannot have lunch break check point)
- /authenticate -> auth an given user

---

## Setting up

1. Clone the project to a given folder

```sh
git clone git@github.com:vitorhugo-pinto/clockingin-api.git
```

2. To run a local database using docker cd to the `/docker` folder and run the command `docker compose up -d`. [For more info about the command](https://docs.docker.com/engine/reference/commandline/compose_up).

![image](https://raw.githubusercontent.com/clizioguedes/images/main/ufrn/acer/sellercenter/ms-base/docker-compose.png)

OBS: Option outside of Docker: run a PostgreSQL whit the configuration of `spring.datasource` (username/password/port) specified in the following folder: `/src/main/resources/application.properties`

---

### Maven

- Skip if using IntelliJ IDEA, because this IDE comes with integrated JDK setup.

- Run `mvn --version`. To check if the dependency manager is installed. If not check the following links to properly install it:

- [Maven no MacOS](https://www.digitalocean.com/community/tutorials/install-maven-mac-os).
    - [Maven no Windows / Linux](https://www.baeldung.com/install-maven-on-windows-linux-mac)

---

### Run it with your favorite IDE

- [IntelliJ IDEA](https://www.jetbrains.com/idea/download)
- [Eclipse IDE](https://www.eclipse.org/downloads/packages/installer)
- [VS Code](https://code.visualstudio.com/download)
  - For the VS Code user, check out this amazing guide for Java in it. By [Loiane Groner](https://github.com/loiane)
- [VS Code for Java - Complete Guide by Loiane Groner](https://loiane.com/2024/03/visual-studio-code-for-java-the-complete-guide/)

---

## Folder structure

- `/docker` - for any docker configuration necessary.

- `/controllers`

- `/mappers` - for converting a Entity to a DTO and vice-versa .

- `/models` - for entities

- `/repositories`

- `/services`

- `/utils` - any given class to support the application
