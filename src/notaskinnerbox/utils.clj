(ns notaskinnerbox.utils)

(defn running-on-google? []
  "Return True if we are running on Google's production servers."
  (let [gae-env (System/getProperty "com.google.appengine.runtime.environment")]
    (and (= gae-env "Production"))))
