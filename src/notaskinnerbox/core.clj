(ns notaskinnerbox.core
  (:require [appengine-magic.core :as ae]))


(defn notaskinnerbox-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, world!"})


(ae/def-appengine-app notaskinnerbox-app #'notaskinnerbox-app-handler)