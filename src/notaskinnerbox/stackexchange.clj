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
  (do
    (println n)
    (* n 1000 60 60 24)))


(defn top-posts-url
  [site n]
  (let [fromdate (long (/ (- (now) (n-days-timestamp n)) 1000))]
    ;; `site` is being ignored for now
    (str "http://api.stackoverflow.com/1.1/questions?fromdate="
         fromdate
         "&sort=votes&tagged=clojure")))


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
  [site n]
   (slurp (curl-gzip (top-posts-url site n))))


(defn top-posts
  [site n]
  (parse-questions-json
   (read-json (top-posts-raw site n))))


