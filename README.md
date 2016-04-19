# emender-service

REST API and web-based service to control Emender and to show test results generated by Emender.



## Prerequsities

### Installation

1. JVM version 6 or better
1. Leiningen

### For the already compiled service

1. JRE version 6 or better (JRE 7 is recommended)



## Installation

1. Clone the emender-service repository
1. Run the following command:
    $ make all



## Usage

To start the service, run the following command:
    $ make run



## Configuration

All options needs to be provided in the configuration file emender-service.ini

### Content of emender-service.ini



## Usage

Emender-service can be controlled via set of simple REST API calls. Every supported API calls are described below:


### Get info about Emender service API

Address: http://$hostname:$port/api/info

Method:  GET

Example:

    $ curl -v localhost:3000/api/info

Return value (example):

    {"toasterNotifications":["info|Api response|<strong>Emender Service<\/strong> api v1 on<\/br>$hostname"],"configuration":$configuration}



### Get list of all books (ie. already tested books)

Address: http://$hostname:$port/api/v1/books
         http://$hostname:$port/api/v1/book-list

Method:  GET

Example:

    $ curl -v localhost:3000/api/v1/books
    $ curl -v localhost:3000/api/v1/book-list

Return value (example):

    ["doc-Other-Book (test)","doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US (test)"]



### Get list of all test jobs

Address: http://$hostname:$port/api/v1/jobs
         http://$hostname:$port/api/v1/job-list

Method:  GET

Example:

    $ curl -v localhost:3000/api/v1/jobs
    $ curl -v localhost:3000/api/v1/job-list

Return value (example):

    [{"id":1,"job":"doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US (test)"},
     {"id":2,"job":"doc-Other-Book (test)"},
     {"id":3,"job":"doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US (test)"}]



## License

Copyright © 2016 Pavel Tisnovsky <ptisnovs@redhat.com>, Red Hat

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

