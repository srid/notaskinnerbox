(ns notaskinnerbox.views
  (:require [notaskinnerbox.stackexchange :as sx]
            [net.cgrand.enlive-html :as html])
  (:use [hiccup core page-helpers]
        [ring.util.response :only [response]])
  (:import [java.text SimpleDateFormat]))


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

(html/deftemplate layout "views/layout.html" [{:keys [title]} content]
  [:.page-title] (html/content title)
  [:#content]    (html/content content))

(html/defsnippet digest "views/digest.html" [:body :> html/any-node]
  [site posts]
  [:article] (html/clone-for
              [{:keys [title score question_id creation_date] :as item} posts]
              [:span.title]            (html/content
                                        {:tag :a, :attrs {:href (item-url item site)},
                                         :content [title]})
              [:span.meta :span.score] (html/content (str score))
              [:span.meta :time]       (html/content (format-date creation_date))))

(defn view-digest
  [site tag n]
  (let [title (page-title site tag n)
        posts (sx/top-posts site tag n)]
    (fn [r]
      (->> (digest site posts)
           (layout {:title title})
           response))))

