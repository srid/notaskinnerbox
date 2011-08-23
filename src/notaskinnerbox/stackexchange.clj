(ns notaskinnerbox.stackexchange
  (:use [clojure.string :only (join split)]
        [clojure.data.json :only (read-json)]
        [clojure.contrib.io :only (to-byte-array)])
  (:import [java.util.zip GZIPInputStream]
           [java.net URL URLEncoder]
           [java.util Date]))


(defn now
  []
  (-> (Date.) (.getTime)))


(defn n-days-timestamp
  [n]
  (* n 1000 60 60 24))


(defn- url-encode
  "Wrapper around java.net.URLEncoder returning a (UTF-8) URL encoded
representation of text."
  [text]
  (URLEncoder/encode text "UTF-8"))


(defn- encode-body-map
  "Turns a map into a URL-encoded string suitable for a request body."
  [body]
  (join "&" (map #(join "=" (map url-encode %)) body)))


(defn top-posts-url
  [site tag n]
  (let [fromdate
        (if (> n 0)
          (long (/ (- (now) (n-days-timestamp n)) 1000))
          "")]
    (str "http://api." site "/1.1/questions??"
         (encode-body-map {"fromdate" (str fromdate)
                           "sort" "votes"
                           "tagged" (str tag)}))))


(defn curl-gzip
  [url]
  "cat an URL as gzip stream"
  (with-open
      [in (-> url (URL.) (.openConnection) (.getInputStream) (GZIPInputStream.))]
    (to-byte-array in)))


(defn parse-questions-json
  [j]
  (map #(select-keys % [:title :question_id]) (:questions j)))


(defn top-posts-raw
  [site tag n]
   (slurp (curl-gzip (top-posts-url site tag n))))


(defn top-posts
  [site tag n]
  (parse-questions-json
   (read-json (top-posts-raw site tag n))))


