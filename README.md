
# Automation API Test

This is an automation API Test project that uses Rest Assured and is run using Dockerfile.

## Acknowledgements

- [Reference API Documentation](https://fakeapi.platzi.com/en/rest/products/)


## How To Use

### Running using Dockerfile

Build Dockerfile

```bash
  docker build -t <your-image-name> .
```

Run Docker image

```bash
  docker run -p 8080:8080 <your-image-name>
```

### Running locally

Build dependency

```bash
  mvn install
```

Running test run

```bash
  mvn test "-DsuiteXmlFile=testng.xml"
```
    