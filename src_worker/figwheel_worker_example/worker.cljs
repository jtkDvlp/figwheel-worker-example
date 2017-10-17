(ns figwheel-worker-example.worker
  (:require [cljs-workers.worker :as worker]))

(enable-console-print!)

(defonce build (atom 0))

(worker/register
 :mirror
 (fn [arguments]
   (assoc arguments
          :worker "I am alive!"
          :build @build)))

(worker/bootstrap)
(swap! build inc)
(print "I'm a worker and I got bootstrapped!")
