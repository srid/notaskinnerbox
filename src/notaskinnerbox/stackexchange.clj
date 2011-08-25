(ns notaskinnerbox.stackexchange
  (:use [clojure.string :only (join split)]
        [clojure.data.json :only (read-json)]
        [clojure.contrib.io :only (to-byte-array)])
  (:import [java.util.zip GZIPInputStream]
           [java.net URL URLEncoder]
           [java.util Date]))


(defn- now
  "Return current timestamp in seconds"
  []
  (long (/ (-> (Date.) (.getTime)) 1000)))


(defn- days->seconds
  [n]
  (* n 60 60 24))


(defn- url-encode
  "Wrapper around java.net.URLEncoder returning a (UTF-8) URL encoded
representation of text."
  [text]
  (URLEncoder/encode (name text) "UTF-8"))


(defn- encode-body-map
  "Turns a map into a URL-encoded string suitable for a request body."
  [body]
  (join "&" (map #(join "=" (map url-encode %)) body)))


(defn- top-posts-url
  "Return the API url for top votted posts from `site` tagged `tag` during last
  `n` days

  Ignore `n` when `n` is zero."
  [site tag n]
  (str "http://api." site "/1.1/questions?"
       (encode-body-map {:fromdate (if (> n 0)
                                     (str (- (now) (days->seconds n)))
                                     "")
                         :sort "votes"
                         :tagged (str tag)
                         :pagesize "30"})))


(defn- curl-gzip
  "Fetch the given URL and always gzip-decompress the response"
  [url]
  "cat an URL as gzip stream"
  (with-open
      [in (-> url (URL.) (.openConnection) (.getInputStream) (GZIPInputStream.))]
    (to-byte-array in)))


(defn- parse-questions-json
  [j]
  (:questions j))


(defn top-posts-raw
  [site tag n]
   (slurp (curl-gzip (top-posts-url site tag n))))


(defn top-posts
  [site tag n]
  (parse-questions-json
   (read-json (top-posts-raw site tag n))))
