// This will always run and confirm the file is loading
console.log("GSAP card hover script loaded");


document.addEventListener("DOMContentLoaded", function () {

    const cards = document.querySelectorAll(".card");

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
                duration: 0.4,
                ease: "power1.out"
            });
        });

        card.addEventListener("mouseleave", () => {
            gsap.to(card, {
                scale: 1,
                boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.3)",
                duration: 0.4,
                ease: "power1.inOut"
            });
        });
    });

})
