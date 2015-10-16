# client

This project is generated with [yo angular generator](https://github.com/yeoman/generator-angular)
version 0.12.1.

## Build & development

Run `grunt` for building and `grunt serve` for preview.

## Testing

Running `grunt test` will run the unit tests with karma.

## Docker
Building docker image
```
grunt build && docker build -t goldenlink-client .
```
Running docker image 
```
docker run -p 80:80 goldenlink-client
```
