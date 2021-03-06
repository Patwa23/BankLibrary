## **Bank Library**

A Spring Boot application which exposes these REST endpoints:

Technology - Java 11,Maven,Docker,Swagger 

#### **Run Docker file**
* docker -v
* docker build -f Dockerfile -t docker-rabobank-library .
* docker images
* docker run -p 8081:8081 docker-rabobank-library
* docker ps -a
* docker stop <container-id>

#### **SWAGGER UI :**
http://localhost:8081/prod/api/swagger-ui.html

#### **Monitoring & Management over HTTP**
http://localhost:9091/manage

#### Service

##### •	`GET /books - http://localhost:8081/prod/api/books`

Returns a list of books. The books have an ISBN number, title and author. 

Output: 
[
    {
        "isbnNumber": "1",
        "title": "Lord of the Ring",
        "author": "Lord"
    },
    {
        "isbnNumber": "2",
        "title": "Lord of the earth",
        "author": "Arnold"
    },
    {
        "isbnNumber": "3",
        "title": "Lord of the space",
        "author": "Earth"
    }
]

##### •	`GET /books/<isbn> - http://localhost:8081/prod/api/books/1`

Returns the book with the given ISBN number. An additional field on the book object “summary” is also returned.

Output:
{
    "isbnNumber": "1",
    "title": "Lord of the Ring",
    "author": "Lord",
    "summary": "This is European Book"
}

##### `•	GET /search<query> - http://localhost:8081/prod/api/books/?author=earth&title=ring`

Returns the books which match the query. You should be able to search for partial titles and authors. I.e. a search for “lord” finds “lord of the rings”.

##### **NOTE:**
* As per my understanding , it has been done "?author=earth&title=lord" as query parameter But,it can also be done as "search=lord" ,which will search in title and author.
* Endpoint of 1st & 3rd service is same.          
Output:
[
    {
        "isbnNumber": "1",
        "title": "Lord of the Ring",
        "author": "Lord"
    },
    {
        "isbnNumber": "3",
        "title": "Lord of the space",
        "author": "Earth"
    }
]

•	`POST /order	- http://localhost:8081/prod/api/books`

**Input:**
[
	{
	    "isbnNumber": "1",
	    "title": "Lord of the Ring",
	    "author": "Lord",
	    "summary": "This is Math Book"
	}
]

This endpoint should allow the user to order multiple books. Use some form of validation to verify the payloads content.
Make an async call to https://jsonplaceholder.typicode.com/posts  to simulate a call to a backend service.

Output:
  "0": {
    "isbnNumber": "1",
    "title": "Lo",
    "author": "Lord",
  },
  "id": 101
}



