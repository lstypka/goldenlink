Building project
```
./gradlew clean build
```

Building docker image
```
docker build -t goldenlink-backend .
```

Running docker image
```
docker run -p 8080:8080 goldenlink-backend
```

Running database
```
cd machines/database/
```
```
vagrant up
```
