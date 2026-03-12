const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            activeView: 'guitars',
            guitars: [],
            brands: [],
            loading: true,
            startDate: '',
            endDate: '',
            absMinDate: '',
            absMaxDate: '',
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
        // Formats database date strings (YYYY-MM-DD) to friendly table format (DD/MM/YYYY)
        formatDateForDisplay(dateStr) {
            if (!dateStr) return 'N/A';
            const parts = dateStr.split('-');
            if (parts.length !== 3) return dateStr;
            const [year, month, day] = parts;
            return `${day}/${month}/${year}`;
        },

        // Scans the entire database once on load to find the global 1998 and 2023 range
        async fetchDateBounds() {
            try {
                const response = await fetch('/api/guitarstore/v1/guitars');
                if (response.ok) {
                    const allGuitars = await response.json();
                    const dates = allGuitars
                        .map(g => g.manufactureDate) 
                        .filter(d => d)
                        .sort();

                    if (dates.length > 0) {
                        this.absMinDate = dates[0]; // Oldest record (e.g. 1998-05-05)
                        this.absMaxDate = dates[dates.length - 1]; // Newest record (e.g. 2023-05-20)
                        
                        // Initialize pickers to this full range immediately
                        this.startDate = this.absMinDate;
                        this.endDate = this.absMaxDate;
                        console.log("Global data bounds identified: " + this.absMinDate + " to " + this.absMaxDate);
                    }
                }
            } catch (error) {
                console.error("Error setting picker bounds:", error);
            }
        },

        applyFilters() {
            console.log("Applying filters. start=" + this.startDate + ", end=" + this.endDate);
            this.pagination.currentPage = 0; // Reset to page 1 for results
            this.fetchGuitars();
        },

        clearFilters() {
            // Reset pickers back to the absolute range (1998-2023) instead of blank
            this.startDate = this.absMinDate;
            this.endDate = this.absMaxDate;
            this.pagination.currentPage = 0;
            this.fetchGuitars();
        },

        async fetchGuitars() {
            this.loading = true;
            try {
                // Determine endpoint: use /filter if pickers are active, else /paginated
                const isFiltered = (this.startDate !== this.absMinDate || this.endDate !== this.absMaxDate);
                const endpoint = isFiltered ? 'filter' : 'paginated';
                
                let url = `/api/guitarstore/v1/guitars/${endpoint}?page=${this.pagination.currentPage}&size=${this.pagination.pageSize}`;
                
                if (this.startDate) url += `&start=${this.startDate}`;
                if (this.endDate) url += `&end=${this.endDate}`;

                console.log("Requesting URL:", url);

                const response = await fetch(url);
                if (response.ok) {
                    const data = await response.json();
                    
                    if (data.content) {
                        this.guitars = data.content;
                        this.pagination.totalPages = data.totalPages;
                        this.pagination.totalElements = data.totalElements;
                    } else {
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
    async mounted() {
        // Step 1: Find absolute bounds to restrict pickers
        await this.fetchDateBounds();
        // Step 2: Load initial data
        this.fetchGuitars();
    }
});

app.mount('#app');