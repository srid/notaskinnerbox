(defproject notaskinnerbox "1.0.0-SNAPSHOT"
  :description "Not a Skinner box - we respect your attention reserves"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.cli "0.1.0"]
                 [org.clojure/data.json "0.1.1"]
                 [compojure "0.6.4"]
                 [enlive "1.0.0"]
                 [ring "0.3.11"]]
  :dev-dependencies [[lein-ring "0.4.5"]
                     [appengine-magic "0.4.4"]]
  :ring {:handler notaskinnerbox.routes/app-handler}
  :main notaskinnerbox.deploy)
