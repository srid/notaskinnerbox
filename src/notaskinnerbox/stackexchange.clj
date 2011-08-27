(ns notaskinnerbox.stackexchange
  (:use [clojure.string :only (join split)]
        [clojure.data.json :only (read-json)]
        [clojure.contrib.io :only (to-byte-array)])
  (:import [java.util.zip GZIPInputStream]
           [java.net URL URLEncoder]
           [java.sql Timestamp]
           [java.util Date]))


(defn- now
  "Return current timestamp in seconds"
  []
  (long (/ (-> (Date.) (.getTime)) 1000)))


(defn- days->seconds
  [n]
  (* n 60 60 24))


(defn- to-string
  "Convert o to string as we expect"
  [o]
  (if (keyword? o)
    (name o)
    (str o)))


(defn- url-encode
  "Wrapper around java.net.URLEncoder returning a (UTF-8) URL encoded
representation of text."
  [text]
  (URLEncoder/encode (to-string text) "UTF-8"))


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
       (encode-body-map
        (conj {:sort "votes"
               :tagged tag
               :pagesize "30"}
              (when (pos? n)
                ;; ignore the last 5 days (arbitrary) to give new questions
                ;; sufficient time to become popular (if at all)
                (let [end-day (- (now) (days->seconds 5))]
                  {:fromdate (- end-day (days->seconds n))
                   :todate end-day}))))))


(defn- curl-gzip
  "Fetch the given URL and always gzip-decompress the response"
  [url]
  "cat an URL as gzip stream"
  (println url)
  (with-open
      [in (-> url (URL.) (.openConnection) (.getInputStream) (GZIPInputStream.))]
    (to-byte-array in)))


(defn- convert-timestamps
  "Convert numeric timestamps in JSON into java.sql.Timestamp"
  [json]
  (map (fn [q] (update-in q [:creation_date]
                          #(Timestamp. (* % 1000))))
       json))


(defn- parse-questions-json
  [j]
  (convert-timestamps (:questions j)))


(defn top-posts-raw
  [site tag n]
   (slurp (curl-gzip (top-posts-url site tag n))))


(defn top-posts
  [site tag n]
  (parse-questions-json
   (read-json (top-posts-raw site tag n))))
