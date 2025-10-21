import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';

interface TestData {
  id: string;
  type: string;
  data: any;
  createdAt: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  template: `
    <div class="min-h-screen bg-gray-100">
      <header class="bg-blue-600 text-white p-4">
        <h1 class="text-2xl font-bold">🧪 QA Automation Service Hub</h1>
        <p class="text-blue-200">Generate test data, view reports, and manage QA workflows</p>
      </header>

      <main class="container mx-auto p-6">
        <!-- Test Data Generation Section -->
        <div class="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 class="text-xl font-semibold mb-4">🎲 Test Data Generation</h2>
          
          <div class="flex gap-4 mb-4">
            <select 
              [(ngModel)]="selectedType" 
              class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
              <option value="">Select Data Type</option>
              <option *ngFor="let type of dataTypes" [value]="type">{{type | titlecase}}</option>
            </select>
            
            <input 
              type="number" 
              [(ngModel)]="recordCount" 
              placeholder="Count" 
              min="1" 
              max="100"
              class="px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
            
            <button 
              (click)="generateTestData()" 
              [disabled]="!selectedType || isLoading"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed">
              {{isLoading ? 'Generating...' : 'Generate Data'}}
            </button>
          </div>

          <!-- Generated Data Display -->
          <div *ngIf="generatedData.length > 0" class="mt-6">
            <h3 class="text-lg font-medium mb-3">Generated Data ({{generatedData.length}} records)</h3>
            <div class="max-h-96 overflow-y-auto">
              <div *ngFor="let item of generatedData; let i = index" 
                   class="bg-gray-50 border border-gray-200 rounded-md p-3 mb-2">
                <div class="flex justify-between items-start">
                  <div>
                    <span class="text-sm font-medium text-blue-600">{{item.type | titlecase}} #{{i + 1}}</span>
                    <div class="text-xs text-gray-500">ID: {{item.id}}</div>
                  </div>
                  <span class="text-xs text-gray-400">{{item.createdAt | date:'short'}}</span>
                </div>
                <div class="mt-2">
                  <pre class="text-sm bg-white p-2 rounded border overflow-x-auto">{{item.data | json}}</pre>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Statistics Section -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-semibold text-gray-700">📊 Total Generated</h3>
            <p class="text-3xl font-bold text-blue-600">{{generatedData.length}}</p>
          </div>
          
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-semibold text-gray-700">🎯 Data Types</h3>
            <p class="text-3xl font-bold text-green-600">{{getUniqueTypes().length}}</p>
          </div>
          
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-semibold text-gray-700">⚡ API Status</h3>
            <p class="text-3xl font-bold" [class]="apiStatus === 'Connected' ? 'text-green-600' : 'text-red-600'">
              {{apiStatus}}
            </p>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="bg-white rounded-lg shadow-md p-6">
          <h2 class="text-xl font-semibold mb-4">🚀 Quick Actions</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <button class="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-blue-500 hover:bg-blue-50 transition-colors">
              <div class="text-2xl mb-2">📈</div>
              <div class="font-medium">View Reports</div>
              <div class="text-sm text-gray-500">Analyze test results</div>
            </button>
            
            <button class="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-green-500 hover:bg-green-50 transition-colors">
              <div class="text-2xl mb-2">🔧</div>
              <div class="font-medium">Jenkins Jobs</div>
              <div class="text-sm text-gray-500">Trigger automation</div>
            </button>
            
            <button class="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-purple-500 hover:bg-purple-50 transition-colors">
              <div class="text-2xl mb-2">📋</div>
              <div class="font-medium">Export Data</div>
              <div class="text-sm text-gray-500">Download as JSON/CSV</div>
            </button>
            
            <button class="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-orange-500 hover:bg-orange-50 transition-colors">
              <div class="text-2xl mb-2">⚙️</div>
              <div class="font-medium">Settings</div>
              <div class="text-sm text-gray-500">Configure preferences</div>
            </button>
          </div>
        </div>
      </main>
    </div>
  `,
  styles: []
})
export class AppComponent {
  title = 'QA Automation Service Hub';
  selectedType = '';
  recordCount = 10;
  generatedData: TestData[] = [];
  isLoading = false;
  apiStatus = 'Checking...';
  dataTypes = ['user', 'product', 'order', 'address', 'payment'];

  private baseUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {
    this.checkApiStatus();
  }

  generateTestData() {
    if (!this.selectedType) return;
    
    this.isLoading = true;
    this.generatedData = [];

    this.http.get<TestData[]>(`${this.baseUrl}/testdata/generate/${this.selectedType}?count=${this.recordCount}`)
      .subscribe({
        next: (data) => {
          this.generatedData = Array.isArray(data) ? data : [data];
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error generating test data:', error);
          this.isLoading = false;
          this.apiStatus = 'Error';
        }
      });
  }

  checkApiStatus() {
    this.http.get(`${this.baseUrl}/testdata/templates`)
      .subscribe({
        next: () => {
          this.apiStatus = 'Connected';
        },
        error: () => {
          this.apiStatus = 'Disconnected';
        }
      });
  }

  getUniqueTypes(): string[] {
    return [...new Set(this.generatedData.map(item => item.type))];
  }
}