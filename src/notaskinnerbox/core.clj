(ns notaskinnerbox.core
  (:require [notaskinnerbox.stackexchange :as sx])
  (:use [clojure.tools.cli :only (cli required optional)]))


(defn parse-args [args]
  (cli args
       (optional ["-s", "--site", "StackExchange site to operate upon"
                  :default "stackoverflow.com"]
                 #(String. %))
       (optional ["-t", "--tag", "Prune by tag"
                  :default ""]
                 #(String. %))
       (optional ["-n", "--days", "Show top posts from last N days" :default 7]
                 #(Integer. %))
       (optional ["--json", "Dump JSON response from server" :default false])))


(defn -main [& args]
  (let [opts (parse-args args)]
    (if (:json opts)
      (println (sx/top-posts-raw
                (:site opts) (:tag opts) (:days opts)))
      (loop [[item & items] (sx/top-posts
                             (:site opts) (:tag opts) (:days opts))
             [idx & indices] (iterate inc 1)]
        (if item
          (do
            (println (str idx ") " (:title item)))
            (println (str "   "
                          "http://"
                          (:site opts)
                          "/questions/"
                          (:question_id item)
                          "\n"))
            (recur items indices)))))))

