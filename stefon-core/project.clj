(defproject com.nedap.staffing-solutions/stefon "0.5.2-alpha1"

  :description "Asset pipeline ring middleware"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :url "http://github.com/circleci/stefon"

  :signing {:gpg-key "releases-staffingsolutions@nedap.com"}

  :repositories {"releases" {:url      "https://nedap.jfrog.io/nedap/staffing-solutions/"
                             :username :env/artifactory_user
                             :password :env/artifactory_pass}}

  :repository-auth {#"https://nedap.jfrog\.io/nedap/staffing-solutions/"
                    {:username :env/artifactory_user
                     :password :env/artifactory_pass}}

  :deploy-repositories {"clojars" {:url      "https://clojars.org/repo"
                                   :username :env/clojars_user
                                   :password :env/clojars_pass}}

  :dependencies [[ring/ring-core "1.5.0"]
                 [clj-time "0.4.4"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/core.incubator "0.1.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [cheshire "4.0.0"]
                 [commons-codec "1.5"]
                 [com.google.javascript/closure-compiler "r1592"]
                 [clj-v8 "0.1.4"]
                 [clj-v8-native "0.1.4"]
                 [pathetic "0.5.1"]]

  :profiles {:dev        {:dependencies [[bond "0.2.5" :exclusions [org.clojure/clojure]]]}
             :check      {:global-vars {*unchecked-math* :warn-on-boxed
                                        ;; avoid warnings that cannot affect production:
                                        *assert*         false}}

             ;; some settings recommended for production applications.
             ;; You may also add :test, but beware of doing that if using this profile while running tests in CI.
             :production {:jvm-opts    ["-Dclojure.compiler.elide-meta=[:doc :file :author :line :column :added :deprecated :nedap.speced.def/spec :nedap.speced.def/nilable]"
                                        "-Dclojure.compiler.direct-linking=true"]
                          :global-vars {*assert* false}}

             ;; this profile is necessary since JDK >= 11 removes XML Bind, used by Jackson, which is a very common dep.
             :jdk11      {:dependencies [[javax.xml.bind/jaxb-api "2.3.1"]
                                         [org.glassfish.jaxb/jaxb-runtime "2.3.1"]]}

             :test       {:dependencies [[com.nedap.staffing-solutions/utils.test "1.6.2"]
                                         [ring-mock "0.1.4"]]
                          :jvm-opts     ["-Dclojure.core.async.go-checking=true"
                                         "-Duser.language=en-US"]}

             :ci         {:jvm-opts     ["-Dclojure.main.report=stderr"
                                         "-Dnedap.ci.release-workflow.stable-branches=pristine"]
                          :global-vars  {*assert* true} ;; `ci.release-workflow` relies on runtime assertions
                          :dependencies [[com.nedap.staffing-solutions/ci.release-workflow "1.7.0-alpha3"]]}})
