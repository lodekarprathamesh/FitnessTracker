
    // Register ScrollTrigger plugin
    gsap.registerPlugin(ScrollTrigger);


    // 1. Animate IN on a page load
    gsap.fromTo(".right",
        { x: 300, opacity: 0.2 },
        { x: 0, opacity: 1, ease: "power3.out", duration: 3 }
    );


    // Slide bg1 to the left & fade out
    gsap.to(".bg1", {
        scrollTrigger: {
            trigger: ".bg1",
            start: "top top",
            end: "bottom 50%",
            scrub: true
        },
        opacity: 0,
        ease: "none"
    });

    // Slide bg2 in from right & fade in
    gsap.fromTo(".bg2",
        {opacity: 0 },
        {
            scrollTrigger: {
                trigger: ".bg1", // triggers at the same time as bg1 scrolls out
                start: "top top",
                end: "bottom 50%",
                scrub: true
            },

            opacity: 1,
            ease: "none"
        }
    );

    gsap.from(".card", {
        scrollTrigger: {
            trigger: ".card",
            start: "top 70%",
            end: "bottom 70%",
            scrub: true
        },
        x: -200,
        opacity: 0
    });

    gsap.from(".nav-tab", {
        scrollTrigger: {
            trigger: ".nav-tab",
            start: "top 60%",
            end: "bottom 20%",
            scrub: true,
        },
        y: -300,
        x:-200,
        opacity: 0
    });



    const links = document.querySelectorAll(".sidebar a");
    const highlight = document.querySelector(".highlight");
    const cards = document.querySelectorAll(".card");

    function activate(index) {
        // Move highlight behind the selected link
        const link = links[index];
        const rect = link.getBoundingClientRect();
        const sidebarRect = link.parentElement.getBoundingClientRect();

        gsap.to(highlight, {
            x: rect.left - sidebarRect.left,
            width: rect.width,
            duration: 0.3,
            ease: "power2.out"
        });

        // Update active state on links
        links.forEach(l => l.classList.remove("active"));
        link.classList.add("active");

        gsap.set(cards, { clearProps: "transform" });

        cards.forEach((card, i) => {
            card.addEventListener("click", () => activate(i));
        });


        // Toggle cards
        cards.forEach(c => c.classList.remove("active"));
        cards[index].classList.add("active");



    }

    // Initial load
    window.addEventListener("load", () => activate(1));

    // Add click listeners to sidebar links
    links.forEach((link, i) => {
        link.addEventListener("click", () => activate(i));
    });





