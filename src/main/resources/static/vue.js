
const { createApp } = Vue;

createApp({
    data() {
        return {
            guitars: [],
            loading: true
        }
    },
    methods: {
        async fetchGuitars() {
            this.loading = true;
            try {
                const response = await fetch('/api/guitarstore/v1/guitars');
                if (response.ok) {
                    this.guitars = await response.json();
                }
            } catch (error) {
                console.error("Fetch error:", error);
            } finally {
                this.loading = false;
            }
        }
    },
    mounted() {
        // Fetch on initial load
        setTimeout(this.fetchGuitars, 800);
    }
}).mount('#app')