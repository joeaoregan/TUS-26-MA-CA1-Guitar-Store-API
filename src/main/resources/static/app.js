const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            activeView: 'guitars',
            guitars: [],
            brands: [],
            loading: true,
            pagination: {
                currentPage: 0,
                totalPages: 0,
                totalElements: 0,
                pageSize: 5
            }
        }
    },
    watch: {
        activeView(newView) {
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
            try {
                const url = `/api/guitarstore/v1/guitars/paginated?page=${this.pagination.currentPage}&size=${this.pagination.pageSize}`;
                const response = await fetch(url);
                if (response.ok) {
                    const data = await response.json();
                    this.guitars = data.content || [];
                    this.pagination.totalPages = data.totalPages || 0;
                    this.pagination.totalElements = data.totalElements || 0;
                }
            } catch (error) {
                console.error("Fetch error:", error);
            } finally {
                setTimeout(() => { this.loading = false; }, 400);
            }
        },
        async fetchBrands() {
            this.loading = true;
            try {
                const response = await fetch('/api/guitarstore/v1/brands');
                if (response.ok) {
                    this.brands = await response.json();
                }
            } catch (error) {
                console.error("Brand fetch error:", error);
            } finally {
                setTimeout(() => { this.loading = false; }, 400);
            }
        },
        nextPage() {
            if (this.pagination.currentPage < this.pagination.totalPages - 1) {
                this.pagination.currentPage++;
                this.fetchGuitars();
            }
        },
        prevPage() {
            if (this.pagination.currentPage > 0) {
                this.pagination.currentPage--;
                this.fetchGuitars();
            }
        },
        refreshData() {
            if (this.activeView === 'guitars') {
                this.fetchGuitars();
            } else {
                this.fetchBrands();
            }
        }
    },
    mounted() {
        this.fetchGuitars();
    }
});

app.mount('#app');