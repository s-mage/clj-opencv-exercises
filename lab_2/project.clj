(defproject lab_2 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [opencv/opencv "3.0.0"]
                 [opencv/opencv-native "3.0.0"]
                 [incanter/incanter "1.5.5"]]
  :repl-options {:init-ns lab-2.core
                 :init (use 'lab-2.core :reload)}
  :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)])

