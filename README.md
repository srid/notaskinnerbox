# notaskinnerbox

This is my first Clojure project. It was originally meant to be a generic
version of [weekly reddit](http://weeklyreddit.appspot.com/) (which I wrote
using Python and deployed to App Engine) -- idea is to get a weekly digest of
any frequently updating sites. I wanted to do this for Hacker News and
stackoverflow.com initially.

At its present state, "weekly stackoverflow" is almost implemented. When I first
heard of [StackExchange newsletters](http://stackexchange.com/newsletters/), my
enthusiasm for this project began withering away, but I intend to continue
learning Clojure using some other project.

## Usage

This is a web application and you can run the web server using:

    $ lein ring server  # have you run `lein deps`?

The default page gives you the top posts at stackoverflow.com since last
week. To load an arbitrary StackExchange site, go to URL /se/<sitedomain>; eg:
`/se/skeptics.stackexchange.com`. Further, URL format accepts tagname and number
of days; see routes.clj for details.

You can also deploy the app to Heroku or Stackato.

## Command line:

You can retrieve the links from the command line as well:

    $ lein run -m notaskinnerbox.core -t learning -s programmers.stackexchange.com  -n 360
    1) My Dad is impatient with the pace of my learning to program. What do I do?
      http://programmers.stackexchange.com/questions/97785
    2) I still can't figure out how to program?
      http://programmers.stackexchange.com/questions/32425
    [...]
    $

## What's in the name?

Sinner Box is an [operant
conditioning](http://en.wikipedia.org/wiki/Operant_conditioning) chamber used to
train rats. I likened the behaviour of us humans mindlessly clicking links (or
pressing levers) to feel a spurt of novelty (or satisfying hunger). "Fast news",
it seems, has conditioned us to seek this behaviour in greater intensity.

## TODO

* Cache requests to StackExchange api
* RSS!
* UI to select StackExchange site

## License

MIT
