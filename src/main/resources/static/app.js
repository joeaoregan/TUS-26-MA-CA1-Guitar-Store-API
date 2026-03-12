const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            activeView: 'guitars',
            guitars: [],
            brands: [],
            loading: true,
            error: null
        }
    },
    watch: {
        activeView(newView) {
            console.log(`View switched to: ${newView}`);
            if (newView === 'brands' && this.brands.length === 0) {
                this.fetchBrands();
            } else if (newView === 'guitars' && this.guitars.length === 0) {
                this.fetchGuitars();
            }
        }
    },
    methods: {
        async fetchGuitars() {
            this.loading = true;
            this.error = null;
            console.log("Fetching Guitars...");
            try {
                const response = await fetch('/api/guitarstore/v1/guitars');
                if (response.ok) {
                    const data = await response.json();
                    this.guitars = Array.isArray(data) ? data : [];
                    console.log("Guitars loaded:", this.guitars.length);
                } else {
                    throw new Error(`Server responded with ${response.status}`);
                }
            } catch (error) {
                console.error("Guitar Fetch error:", error);
                this.error = "Failed to load guitars.";
            } finally {
                setTimeout(() => { this.loading = false; }, 400);
            }
        },
        async fetchBrands() {
            this.loading = true;
            this.error = null;
            console.log("Fetching Brands...");
            try {
                const response = await fetch('/api/guitarstore/v1/brands');
                if (response.ok) {
                    const data = await response.json();
                    this.brands = Array.isArray(data) ? data : [];
                    console.log("Brands loaded:", this.brands.length);
                } else {
                    throw new Error(`Server responded with ${response.status}`);
                }
            } catch (error) {
                console.error("Brand Fetch error:", error);
                this.error = "Failed to load brands.";
            } finally {
                setTimeout(() => { this.loading = false; }, 400);
            }
        },
        refreshData() {
            console.log("Manual refresh triggered");
            if (this.activeView === 'guitars') {
                this.fetchGuitars();
            } else {
                this.fetchBrands();
            }
        }
    },
    mounted() {
        console.log("Vue App Mounted Successfully");
        this.fetchGuitars();
    }
});

app.mount('#app');