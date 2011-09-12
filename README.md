# notaskinnerbox

## History & Status

This is my first Clojure project. It was originally meant to be a generic
version of [weekly reddit](http://weeklyreddit.appspot.com/) (which I wrote
using Python and deployed to App Engine) -- the idea is to be sent a weekly
digest of any frequently updating site. Initially, I wanted to do this for
Hacker News and stackoverflow.com.

At its present state, "weekly stackoverflow" is mostly implemented but still not
ready for public use.

Although my enthusiasm for this project began withering away when I first heard
of [StackExchange newsletters](http://stackexchange.com/newsletters/), after
actually *reading* their newsletter for a couple of weeks, I came to realize its
pain points:

* I want RSS, not Inbox spam
* I want to filter questions by specific "tag"
* I may want a monthly, instead of weekly, digest

These are not possible with StackExchange newsletters. Furthermore, I am still
interested in creating a weekly digest for Hacker News. Consequently, my interest
on this project came back to full force.

![Screenshot](http://i.imgur.com/R0jN9.png "Screenshot of the web app showing stackoverflow.com digest")

## Usage

Check out the source: 

    $ git clone https://github.com/srid/notaskinnerbox.git
    $ cd notaskinnerbox
    
Download dependencies: 

    $ lein deps
    
This is a web application and you can run it using:

    $ lein ring server

The default page gives you the top posts at stackoverflow.com since last
week. To view an arbitrary StackExchange site, go to URL /se/<sitedomain>; eg:
`/se/skeptics.stackexchange.com`. Further, the URL format optionally accepts a
tagname and the number of days; see `routes.clj` for details.

The app uses Ring, Compojure and Enlive. It can be seemlesly deployed to Heroku
or Stackato.

## Command line:

To retrieve the links directly from the command line:

    $ lein run -m notaskinnerbox.core -t learning -s programmers.stackexchange.com  -n 360
    1) My Dad is impatient with the pace of my learning to program. What do I do?
      http://programmers.stackexchange.com/questions/97785
    2) I still can't figure out how to program?
      http://programmers.stackexchange.com/questions/32425
    [...]
    $

## What's in the name?

Skinner Box refers to the [operant
conditioning](http://en.wikipedia.org/wiki/Operant_conditioning) chamber used to
train rats. I likened the conditioning to us humans mindlessly clicking links
(or pressing the lever) to feel a spurt of novelty (or satisfying hunger). "Fast
news", it seems, has conditioned us to seek this behaviour in ever greater
intensity. This concept is widely known as [information
overload](http://en.wikipedia.org/wiki/Information_overload), but that only
deflects attention (of the addict) away from the psychological behaviour
(addiction).

## Deploying to production

This app can be deployed to Heroku, Stackato or Google App Engine.

## TODO

* Cache API requests
* Weekly cron tasks
* Database
* RSS!
* UI to select StackExchange site
* Hacker News
* Reddit?

## License

[Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0.php)
