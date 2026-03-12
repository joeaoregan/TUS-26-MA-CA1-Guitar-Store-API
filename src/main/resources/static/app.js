const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            activeView: 'guitars', // Toggle state
            guitars: [],
            brands: [],
            loading: true,
            error: null,
            // Date parameters matching your backend 'start' and 'end' keys
            startDate: '',
            endDate: '',
            // Pagination state
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
        applyFilters() {
            // Logs to verify inputs are captured for your /filter endpoint
            console.log("Applying filters. start=" + this.startDate + ", end=" + this.endDate);
            this.pagination.currentPage = 0; // Reset to page 1 for results
            this.fetchGuitars();
        },
        clearFilters() {
            this.startDate = '';
            this.endDate = '';
            this.pagination.currentPage = 0;
            this.fetchGuitars();
        },
        async fetchGuitars() {
            this.loading = true;
            this.error = null;
            try {
                // Determine endpoint based on whether filter dates are provided
                const isFiltered = this.startDate || this.endDate;
                const endpoint = isFiltered ? 'filter' : 'paginated';
                
                let url = `/api/guitarstore/v1/guitars/${endpoint}?page=${this.pagination.currentPage}&size=${this.pagination.pageSize}`;
                
                // Append 'start' and 'end' keys confirmed by your Postman test
                if (this.startDate) url += `&start=${this.startDate}`;
                if (this.endDate) url += `&end=${this.endDate}`;

                console.log("Requesting URL:", url);

                const response = await fetch(url);
                if (response.ok) {
                    const data = await response.json();
                    
                    // Handle Spring Page object vs raw list
                    if (data.content) {
                        this.guitars = data.content;
                        this.pagination.totalPages = data.totalPages;
                        this.pagination.totalElements = data.totalElements;
                    } else {
                        // Fallback for simple list results
                        this.guitars = data;
                        this.pagination.totalPages = 1;
                        this.pagination.totalElements = data.length;
                    }
                }
            } catch (error) {
                console.error("Guitar Fetch error:", error);
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