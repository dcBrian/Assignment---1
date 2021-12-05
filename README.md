# Homework

This is a command line Java program that can fetch web pages and saves them to disk for later retrieval and browsing.<br />
The base program was generated via Spring Initializr.

### Built With

- [Java 11](https://www.java.com/fr/)
- [Spring-Boot](https://spring.io/projects/spring-boot)
- [Jsoup](https://jsoup.org/) : A Java HTML Parser

## Features

### Section 1

- [x] Fetch multiple URLs
- [x] Print Exceptions to the terminal

### Section 2

- [x] Conditionally record & output metadata (--metadata option)

### Extra Credits

- [x] Load Page locally
  - [x] Archive Assets (HTML tags with src attribute)
  - [x] Archive Imports (Link tags) :: Actually results in Origin null CORS issue
        - Start Browser with following option : --allow-file-access-from-files
        - Set up Server

## Resources Folder Structure

    ├── pages                        # Root Resources folder
    │   ├── url1                     # Url1 Resources folder
    │   │    ├── url1.html
    │   │    ├── asset1.extension
    │   │    ├── asset2.extension
    │   │    ├── import1.extension
    │   │    ├── import2.extension
    │   │    └── metadata.txt
    │   └── url 2                    # Url2 Resources folder
    ├── ...

<!-- GETTING STARTED -->

## Getting Started

### Installation with Docker

1. Build image
   ```
   docker build -t homework .
   ```
2. Run container - example 
   ```
   docker run --name homework_container homework --all url1 url2
   ```
3. Download files from container
   ```
   docker cp homework_container:pages "/your/path"
   ```

## Global Options

The cli have the following options available

```
   --metadata  # Record and print metadata
   --assets    # Save Assets
   --imports   # Save imports

   --copy      # ASSETS + IMPORTS
   --all       # METADATA + ASSETS + IMPORTS
```

<!-- CONTACT -->

## Contact

dacruzbrian1@gmail.com
