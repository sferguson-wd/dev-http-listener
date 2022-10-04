# Dev Http Listener

This project creates a basic HTTP listener that can be used to develop and test connections to external APIs.

## Execution

To run the listener, build it using `mvn clean package` and then run it with `mvn exec:java`.

The port defaults to 8080, but can be altered at runtime by setting the `DEV_HTTP_PORT` environment variable.

By default, the response reflects back the request type and input parameters (both query string and POST variables).  This may be overridden by setting the `DEV_RESPONSE_OVERRIDE` environment variable to return a specific response structure, to meet the needs of testing different API responses.
