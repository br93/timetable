
# Timetable

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

This project is an API built using **Java 21, Spring Boot 3, MongoDB, RabbitMQ and Timefold.**

The application was developed to help create a timetable using AI optimization and Google Calendar API integration.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributing](#contributing)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/br93/timetable.git
```

2. Create your credentials in [Console Google Developer](https://console.cloud.google.com/apis/dashboard) and change these config in **docker.env** with the proper values
<br>

**Config Values**

```docker.env
GOOGLE_CLIENT_ID=########
GOOGLE_CLIENT_SECRET=########
```
<br>
3. Run makefile (or Docker Compose)
<br>

**Makefile**
```
make up
```

**Docker Compose**
```
mvn clean package
docker compose up -d
```

## Usage

1. The Spring Boot app will be accessible at http://localhost:8080
* Swagger documentation: http://localhost:8080/swagger-ui.html
2. MongoExpress will be accessible at http://localhost:8081
3. RabbitMQ will be accessible at http://localhost:15672

## Endpoints

**TIMESLOT**
```markdown
POST /timeslot - Create a new timeslot
```

**BODY**
```json
{
  "start": "18:00:00",
  "end": "20:00:00"
}
```

**TIMETABLE**
```markdown
POST /timetable - Create a new timetable
```

**BODY**
```json
[
	{
		"subject": "new-lesson1",
		"level": 1
	},
	{
		"subject": "new-lesson2",
		"level": 2
	},
	{
		"subject": "new-lesson3",
		"level": 3
	},
	{
		"subject": "new-lesson4",
		"level": 3
	},
	{
		"subject": "new-lesson5",
		"level": 2
	},
	{
		"subject": "new-lessons6",
		"level": 2
	}
]
```
Timetable will be generated for up to 10 lessons (5 lesson optional) using Timefold with following constraints:
- One lesson per day (when optional)
- Difficult lessons apart from each other during the week
- Easier lessons on Friday
- If more than 5 lessons, avoid two difficult lessons in the same day
- Avoid two lessons on Friday

![Timetable](https://raw.githubusercontent.com/br93/timetable/main/doc/calendar.png)

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.



