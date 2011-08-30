(ns notaskinnerbox.views
  (:require [notaskinnerbox.stackexchange :as sx]
            [net.cgrand.enlive-html :as html])
  (:use [hiccup core page-helpers]
        [ring.util.response :only [response]])
  (:import [java.text SimpleDateFormat]))


(defn render [t]
  (apply str t))

(def render-to-response
  (comp response render))
  
(defn- item-url [item site]
  (str "http://" site "/questions/"
       (:question_id item)))


(defn format-date
  [date]
  (.format (SimpleDateFormat.  "yyyy-MM-dd") date))


(defn- page-title [site tag n]
  (str "weekly " site
       (if (not (= tag "")) (str " [" tag "] "))
       (if (> n 0) (str " (" n " days)"))))


(html/deftemplate layout "notaskinnerbox/views/layout.html" [{:keys [title]} content]
  [:.page-title] (html/content title)
  [:#content]    (html/content content))


(html/defsnippet digest "notaskinnerbox/views/digest.html" [:body :> html/any-node]
  [{:keys [site posts]}]
  [:article] (html/clone-for [{:keys [title score question_id creation_date]} posts]
                        [:span.title]     (html/content
                                          {:tag :a, :attrs {:href (str "http://" site "/questions/"
                                                                       question_id)},
                                           :content [title]})
                        [:span.meta :span.score] (html/content (str score))
                        [:span.meta :time] (html/content (format-date creation_date))))

(defn view-digest
  [r]
  (->> (digest {:site "stackoverflow.com" :posts (sx/top-posts "stackoverflow.com" "" 7)})
       (layout {:title "Weekly Stackoverflow"})
       response))



(defn index-pageOLD [site tag n]
  (base-page
   (page-title site tag n)
   (for [item (sx/top-posts site tag n)]
     (let [date (format-date (:creation_date item))]
       [:article
        [:a {:href (item-url item site)} (:title item)]
        [:span {:class "meta"}
         [:span " " [:b (:score item)] " votes; "]
         [:span [:time {:datetime date :pubDate "pubDate"} date]]]]))))

(defn base-page
  [title body]
  (html5
    [:head
     [:meta {:charset "UTF8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     [:title (str title " - notaskinnerbox")]
     (include-css "/css/lessframework.css")
     (include-css "http://fonts.googleapis.com/css?family=PT+Sans+Caption")
     (include-css "/css/style.css")]
    [:body {:lang "en"}
     [:header
      [:hgroup
       [:h1 "not a skinner box"]
       [:h3 title]]]
     [:section body]
     [:footer "caffeinated play with clojure "
      [:a {:href "https://github.com/srid/notaskinnerbox"} "on github"]]]))
  
