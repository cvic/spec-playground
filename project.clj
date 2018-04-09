(defproject yetibot-spec "0.0.1"
  :description "Yetibot config specs"
  :url "https://yetibot.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]

  :main yetibot-spec.core

  :repl-options {:init-ns yetibot-spec.core
                 :timeout 120000
                 :welcome (println "Welcome to the yetibot spec playground REPL!")})
