(ns yetibot-spec.core
(:gen-class))

(require '[clojure.pprint :as pprint])
(require '[clojure.spec.alpha :as spec])

(spec/def ::user-id   (spec/and pos-int? #(>= % 1000) #(< % Integer/MAX_VALUE)))
(spec/def ::unique-id (spec/or :name string? :id pos-int?))

(spec/def ::comment-ver1 (spec/or :filled string? :empty nil?))
(spec/def ::comment-ver2 (spec/nilable string?))


(spec/def ::username string?)
(spec/def ::password (spec/and string?
                         #(>= (count %) 8)))
(spec/def ::super-secure-signup (spec/keys :req-un [::username ::password]))


(defn explain-validation->data
    [validation-key value]
    (println "validation-key" validation-key)
    (println "value" value)
    (-> (spec/explain-data validation-key value) (pprint/pprint))
    (println))

(defn -main
    [& args]

    (let [ids [nil [] 1/2 0.5 1 10000000000 "*?"]]
         (doseq [id ids]
             (explain-validation->data ::user-id id)))

    (let [ids [nil [] 1/2 0.5 -1]]
         (doseq [id ids]
             (explain-validation->data ::unique-id id)))

    (explain-validation->data ::super-secure-signup  {:username "foo"
                         :password "123456789"}))
