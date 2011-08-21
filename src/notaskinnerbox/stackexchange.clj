(ns notaskinnerbox.stackexchange
  (:use [clojure.data.json :only (read-json)]
        [clojure.contrib.io :only (to-byte-array)])
  (:import [java.util.zip GZIPInputStream]
           [java.net URL]))


(defn top-posts-url
  [site]
  "http://api.stackoverflow.com/1.1/questions?fromdate=1313203687&sort=votes&tagged=clojure")


(defn curl-gzip
  [url]
  "cat an URL as gzip stream"
  (with-open
      [in (-> url (URL.) (.openConnection) (.getInputStream) (GZIPInputStream.))]
    (to-byte-array in)))


(defn parse-questions-json
  [j]
  (map #(select-keys % [:title :question_url]) (:questions j)))


(defn top-posts-last-week-raw
  [site]
   (slurp (curl-gzip (top-posts-url site))))


(defn top-posts-last-week
  [site]
  (parse-questions-json
   (read-json (top-posts-last-week-raw site))))


