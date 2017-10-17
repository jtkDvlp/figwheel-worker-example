(ns figwheel-worker-example.core
  (:require [cljs-workers.core :as main]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

(defonce worker
  (main/create-one
   (if js/goog.DEBUG
     "js/bootstrap_worker.js"
     "js/compiled/worker.js")))

(defn print-result
  [result-chan]
  (go
    (let [result (<! result-chan)]
      (.debug js/console
              (str (:state result))
              result))))

(print-result
 (main/do-with-worker!
  worker
  {:handler :mirror
   :arguments {:a "Hello" :b "World!" :c 10 :d (str (random-uuid))}}))
