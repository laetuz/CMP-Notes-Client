import SwiftUI

@main
struct iOSApp: App {

    init() {
        AppModulesKt.initializeKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}