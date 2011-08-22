(ns notaskinnerbox.stackexchange
  (:use [clojure.data.json :only (read-json)]
        [clojure.contrib.io :only (to-byte-array)])
  (:import [java.util.zip GZIPInputStream]
           [java.net URL]
           [java.util Date]))


(defn now
  []
  (-> (Date.) (.getTime)))


(defn n-days-timestamp
  [n]
  (* n 1000 60 60 24))


(defn top-posts-url
  [site tag n]
  (let [fromdate (long (/ (- (now) (n-days-timestamp n)) 1000))]
    (str "http://api."
         site
         "/1.1/questions?fromdate="
         fromdate
         "&sort=votes&tagged="
         tag)))


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


