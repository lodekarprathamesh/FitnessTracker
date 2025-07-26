// This will always run and confirm the file is loading
console.log("GSAP card hover script loaded");

// Delay 500ms to allow dynamic HTML (like Thymeleaf loops) to render
setTimeout(() => {
    const cards = document.querySelectorAll(".workout-card");

    console.log("Found cards after delay:", cards.length);

    if (cards.length === 0) {
        console.warn("No .workout-card elements found.");
        return;
    }

    // Apply GSAP animations to each card
    cards.forEach((card) => {
        card.addEventListener("mouseenter", () => {
            gsap.to(card, {
                scale: 1.05,
                boxShadow: "0px 12px 24px rgba(255, 0, 0, 0.2)",
                duration: 0.3,
                ease: "power1.out"
            });
        });

        card.addEventListener("mouseleave", () => {
            gsap.to(card, {
                scale: 1,
                boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.3)",
                duration: 0.3,
                ease: "power1.inOut"
            });
        });
    });
}, 500);  // 500ms delay after DOM load
