# PAI @ DMCS

## Using git and GitLab

We need to use HTTPS (not SSH) to connect to the GitLab server at DMCS. You can safely dismiss the message about adding SSH keys.

You can use [this tutorial to automatically remember your GitLab password in the system keychain][github-keychain] so you will not have to reenter it for each push.

Because the HTTPS certificate used for our GitLab server is not signed by a recognized certificate authority you need to disable certificate verification (once in each repository):

```
git config http.sslVerify "false"
```

[github-keychain]: https://help.github.com/articles/caching-your-github-password-in-git/

## Lab1 - wc

## Lab2 - HTTP

### Lab2 A

Your first task is to write a program that accepts one or more URLs from the command line, downloads them and parses the resulting HTML. The program should print three separate lists with all the links, images and `mailto:` links. Both links and images should be resolved to absolute URLs. Please save the contents of the `<head>` tag in the HTML document to a file named `head.txt`.

You can use the [jsoup][jsoup] library for parsing and processing HTML.

A good web page you can test your program on is `http://debian.org`.

Stuff to think about:

 * Does the [DMCS "Employees"](https://www.dmcs.p.lodz.pl/pracownicy) web page show any e-mail addresses? Why not?
 * [In Java, how to append a string more efficiently?](http://stackoverflow.com/questions/12899953/in-java-how-to-append-a-string-more-efficiently)

[jsoup]: http://jsoup.org

### Lab2 B

The second task is to get the page by making the connection "manually" using Sockets and the HTTP protocol. You may fail for all response codes other than 200 (this includes errors and redirections) but you should print a warning message about it.

The program should also print:

 * the remote IP address
 * the local TCP port
 * the size of the reply
 * the time which elapsed from sending the request to getting the reply.

A very simple HTTP request looks like this:

```
GET / HTTP/1.0\r\n
\r\n
```

Requirements:

 * The argument to the program is a URL so use the appropriate class to get all the parts needed to make a successful connection.
 * The hostname, port and path should be extracted from the given URLs, not hardcoded.
 * Pass an IP address to the Socket() constructor, not the domain name.
 * The line endings have to be precisely "\r\n".
 * Keep output buffer flushing in mind when using e.g. the PrintWriter class.

Stuff to think about:

 * What do you get in the reply apart from the actual HTML?
 * What happens when you change the version to `HTTP/1.1`
 * Try connecting to `http://histmag.org` and check if the reply is what you would expect. Why not? (lookup "virtual hosting")

## Lab3 - echo

A simple echo server and echo client (two programs). The server opens a listening TCP socket and waits for clients. After a client connects any data received by the server is sent back. This continues until the client terminates the connection. The first version should be single-threaded.

Afterwards please check when happens with two concurrent clients when they wait a bit before closing the connection. Fix the problem by making the server multi-threaded.

## Lab4 - xlocks

Write a program that shows one of the following behaviours: deadlock, livelock and starvation. The kind of behaviour should be selectable from the command line. Please add a timeout mechanism so the program does not halt indefinitely.

A good article with an example of how well-intended synchronization algorithms can lead to a thread starvation problem is [the Wikipedia article about the readers-writers problem][rw-problem].

[rw-problem]: https://en.wikipedia.org/wiki/Readers–writers_problem

## Lab5 & project - "BlaBlaCar"

A serious "full-stack" application for a carpooling service. The server in Java maintains an in-memory database of car trips and handles requests from the clients (please write a simple database instead of using an off-the-shelf library/server). The clients are web applications written in JavaScript and communicate with the server over HTTP *and WebSockets*. Each trip has an owner, start date, starting place, destination, price and the amount of free seats. A user can either add or cancel a trip or reserve a seat on an existing trip.

Users are recognized by their e-mail and password. Each user should also provide their full name which is displayed to other users (the e-mail is kept private). *Users are notified in real time about any changes to their trips.* Each user can *subscribe to* (search for) one starting place + destination pair *and be notified in real time when a matching trip is available*. The data should be serialized to disk so it survives server restarts.

For server side we will use the [Spark][spark] micro-framework. On the client is would be a good idea to use [jQuery][jquery] *and [WebSockets][websockets]. To make writing the app easier it is a good idea to use [knockout.js][knockout].* For logging in you can use either [HTTP Basic authentication][http-basic] or sessions and cookies needed. You can use [this mailing list message][http-basic-spark] for inspiration.

Parts of the above marked in italics (real-time updates, knockout.js and the usage of WebSockets) are not required to get the mark of "3". Despite that the application should remain a [single page application][spa] with a button to manually refresh the displayed content.

[spark]: http://sparkjava.com
[jquery]: http://jquery.com
[websockets]: https://developer.mozilla.org/en-US/docs/Web/API/WebSocket
[knockout]: http://knockoutjs.com
[http-basic]: https://en.wikipedia.org/wiki/Basic_access_authentication
[http-basic-spark]: https://groups.google.com/forum/#!topic/sparkjava/HymQ04gVTNs
[spa]: https://en.wikipedia.org/wiki/Single-page_application
