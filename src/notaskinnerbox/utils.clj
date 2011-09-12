(ns notaskinnerbox.utils
  (:use [clojure.contrib.string :only (substring?)]))

(defn running-on-google? []
  (substring? "Google App Engine"
              (get (System/getenv) "SERVER_SOFTWARE" "")))
