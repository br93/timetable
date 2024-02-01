up: build
	@echo "Starting services..."
	docker compose up -d
	@echo "Done"

down:
	@echo "Stopping services..."
	docker compose down
	@echo "Done"

build:
	@echo "Building Spring Boot service..."
	mvn clean package
	@echo "Done"

logs:
	docker logs timetable