for run docker database use:
`
docker run --name cofee_house -p 5432:5432 -e POSTGRES_USER=cofee_house -e POSTGRES_PASSWORD=cofee_house -e POSTGRES_DB=cofee_house -d postgres:13.3
`

env variable for run:
`
DB_HOST=localhost;DB_PORT=5432;DB_NAME=cofee_house;POSTGRES_USER=cofee_house;POSTGRES_PASSWORD=cofee_house;
`
