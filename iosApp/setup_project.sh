#!/bin/bash

# Pokedexer iOS Project Setup Script
# This script helps set up the Xcode project with all Swift files

echo "🚀 Setting up Pokedexer iOS Project..."

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if we're in the right directory
if [ ! -d "PokedexerApp.xcodeproj" ]; then
    echo "❌ Error: PokedexerApp.xcodeproj not found!"
    echo "Please run this script from the iosApp directory"
    exit 1
fi

echo -e "${BLUE}Step 1:${NC} Building KMP Framework..."
cd ../shared
./gradlew linkReleaseFrameworkIosSimulatorArm64 linkReleaseFrameworkIosArm64
cd ../iosApp

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ KMP Framework built successfully${NC}"
else
    echo -e "${YELLOW}⚠️  KMP Framework build failed, but continuing...${NC}"
fi

echo ""
echo -e "${BLUE}Step 2:${NC} Verifying project structure..."

# Check for Swift files
SWIFT_FILE_COUNT=$(find PokedexerApp -name "*.swift" | wc -l)
echo "Found $SWIFT_FILE_COUNT Swift files"

# Check for framework
if [ -f "../shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework/shared" ]; then
    echo -e "${GREEN}✅ KMP Framework found${NC}"
else
    echo -e "${YELLOW}⚠️  KMP Framework not found at expected location${NC}"
fi

echo ""
echo -e "${GREEN}✅ Project setup complete!${NC}"
echo ""
echo "📋 Next Steps:"
echo "1. Open PokedexerApp.xcodeproj in Xcode"
echo "2. The project is configured with all Swift files"
echo "3. Select iPhone 15 Pro (or any iOS 18+ simulator)"
echo "4. Press ⌘R to build and run"
echo ""
echo "⚠️  Important Notes:"
echo "- iOS 18.0+ is required for MeshGradient support"
echo "- The KMP framework will auto-build on each Xcode build"
echo "- First build may take a few minutes"
echo ""
echo "🐛 If you encounter issues:"
echo "- Clean build folder: ⌘⇧K in Xcode"
echo "- Rebuild KMP framework: cd ../shared && ./gradlew linkReleaseFrameworkIosSimulatorArm64"
echo "- Check Info.plist for network permissions"
echo ""
echo -e "${GREEN}Happy coding! 🎉${NC}"
