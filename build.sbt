import com.github.play2war.plugin.{Play2WarKeys, Play2WarPlugin}

name := "products"

version := "0.1"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "ws.securesocial" %% "securesocial" % "2.1.4",
  "com.github.mumoshu" %% "play2-memcached" % "0.4.0"
)

play.Project.playScalaSettings

play.Keys.templatesTypes ++= Map("stream" -> "ui.HtmlStreamFormat")

play.Keys.templatesImport ++= Vector("_root_.ui.HtmlStream", "_root_.ui.HtmlStream._")

resolvers += "Spy Repository" at "http://files.couchbase.com/maven2"

