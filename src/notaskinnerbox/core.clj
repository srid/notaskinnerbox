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
       (required ["-s", "--site", "StackExchange site to operate upon"]
                 #(String. %))
       (optional ["--json", "Dump JSON response from server" :default false])))


(defn -main [& args]
  (let [opts (parse-args args)]
    (if (:json opts)
      (println (sx/top-posts-last-week-raw (:site opts)))
      (loop [items (sx/top-posts-last-week  (:site opts))]
        (if items
          (do
            (println (first items))
            (recur (next items))))))))


(ae/def-appengine-app notaskinnerbox-app #'notaskinnerbox-app-handler)
