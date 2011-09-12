;; This had to go to a separate file as App Engine doesn't like using
;; `ring.adapter.jetty
(ns notaskinnerbox.heroku
  (:use notaskinnerbox.routes
        ring.adapter.jetty))

;; Entry point for Heroku/Stackato
(defn -main []
  (let [env (System/getenv)
        port (Integer/parseInt
              (get env "VCAP_APP_PORT"
                   (get env "PORT" "8080")))]
    (println (str "Starting at " port))
    (run-jetty app-handler {:port port})))

