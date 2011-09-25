;; This had to go to a separate file as App Engine doesn't like using
;; `ring.adapter.jetty
(ns deploy
  (:use notaskinnerbox.routes
        [notaskinnerbox.app_servlet :only (notaskinnerbox-app)]
        ring.adapter.jetty)
  (require [appengine-magic.core :as ae]))

(defn ae-develop
  "Launch, or restart, the App Engine dev server after reloading namespaces"
  ([] (ae-develop []))
  ([namespaces]
     (when namespaces
       (use :reload-all namespaces))
     (println "Launching server at http://localhost:8080/")
     (ae/serve notaskinnerbox-app)))

;; Entry point for Heroku/Stackato
(defn -main []
  (let [env (System/getenv)
        port (Integer/parseInt
              (get env "VCAP_APP_PORT"
                   (get env "PORT" "8080")))]
    (println (str "Starting at " port))
    (run-jetty app-handler {:port port})))

