(ns notaskinnerbox.core
  (:require [appengine-magic.core :as ae])
  (:require [notaskinnerbox.stackexchange :as sx])
  (:use [clojure.tools.cli :only (cli required optional)]))

(defn notaskinnerbox-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world!"})


(defn parse-args [args]
  (cli args
       (optional ["-s", "--site", "StackExchange site to operate upon"
                  :default "stackoverflow"]
                 #(String. %))
       (optional ["-n", "--days", "Show top posts from last N days" :default 7]
                 #(Integer. %))
       (optional ["--json", "Dump JSON response from server" :default false])))


(defn -main [& args]
  (let [opts (parse-args args)]
    (if (:json opts)
      (println (sx/top-posts-raw (:site opts) (:days opts)))
      (loop [[item & items] (sx/top-posts (:site opts) (:days opts))
             [idx & indices] (iterate inc 1)]
        (if item
          (do
            (println (str idx ") " (:title item)))
            (println (str "  http://stackoverflow.com/questions/"
                          (:question_id item)))
            (recur items indices)))))))


(ae/def-appengine-app notaskinnerbox-app #'notaskinnerbox-app-handler)
