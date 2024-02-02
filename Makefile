up:
	@echo "Starting services..."
	docker compose up -d
	@echo "Done"

down:
	@echo "Stopping services..."
	docker compose down
	@echo "Done"
	
logs:
	docker logs timetable