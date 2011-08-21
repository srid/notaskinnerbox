(ns notaskinnerbox.core
  (:require [appengine-magic.core :as ae])
  (:require [notaskinnerbox.stackexchange :as sx])
  (:use [clojure.tools.cli :only (cli required)]))

(defn notaskinnerbox-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world!"})


(defn parse-args [args]
  (cli args
       (required ["-s", "--site", "StackExchange site to operate upon"]
                 #(String. %))))


(defn -main [& args]
  (let [opts (parse-args args)]
    (println (sx/top-posts-last-week  (:site opts)))))


(ae/def-appengine-app notaskinnerbox-app #'notaskinnerbox-app-handler)
