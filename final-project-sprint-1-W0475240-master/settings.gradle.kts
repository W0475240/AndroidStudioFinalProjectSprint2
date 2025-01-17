pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoidzA0NzUyNDAiLCJhIjoiY2x1enVjMGN1MWd5czJ3bW9nYnk1ZTdmZCJ9.b7ozwlg2Fh2d8TSiWcQKxg"
            }
        }
    }
}




rootProject.name = "TransitApp"
include(":app")
 