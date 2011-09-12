(ns notaskinnerbox.utils
  (:use [clojure.contrib.string :only (substring?)]))

(defn running-on-google? []
  "Return True if we are running on Google's production servers."
  (let [gae-env (System/getProperty "com.google.appengine.runtime.environment")]
    (println "runtime: " gae-env)
    (and gae-env
         (= gae-env "Production"))))
