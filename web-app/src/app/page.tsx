'use client';

import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useState, useCallback } from 'react';
import { useLeagueWebSocket } from '@/hooks/useLeagueWebSocket';

const API_BASE = 'http://localhost:8080/api';

async function getLeagues() {
  const res = await fetch(`${API_BASE}/leagues`);
  if (!res.ok) throw new Error('Failed to fetch leagues');
  return res.json();
}

async function createLeague(name: string) {
  const res = await fetch(`${API_BASE}/leagues`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name }),
  });
  if (!res.ok) throw new Error('Failed to create league');
  return res.json();
}

export default function Home() {
  const queryClient = useQueryClient();
  const [newLeagueName, setNewLeagueName] = useState('');

  const leaguesQuery = useQuery({
    queryKey: ['leagues'],
    queryFn: getLeagues,
  });

  const createMutation = useMutation({
    mutationFn: createLeague,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['leagues'] });
      setNewLeagueName('');
    },
  });

  // WebSocket callback: add new league instantly
  const handleLeagueUpdate = useCallback((newLeague: any) => {
    console.log('🆕 Updating cache with:', newLeague);

    // Optimistically add to React Query cache
  queryClient.setQueryData(['leagues'], (oldData: any[]) => {
    if (!oldData) return [newLeague];
    
    // Check if league already exists (avoid duplicates)
    const exists = oldData.some((l: any) => l.id === newLeague.id);
    if (exists) return oldData;
    
    return [...oldData, newLeague];
  });
  
  console.log('✅ Cache updated!');
  }, [queryClient]);

  // Connect WebSocket
  useLeagueWebSocket(handleLeagueUpdate);

  return (
    <main className="min-h-screen bg-gradient-to-br from-green-50 to-blue-50 p-8">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-4xl font-bold text-gray-900 mb-8 text-center">
          🏏 CricLeaguePro
        </h1>
        
        {/* Create league form */}
        <div className="bg-white p-6 rounded-xl shadow-lg mb-8">
          <h2 className="text-xl font-semibold mb-4">Create New League</h2>
          <div className="flex gap-4">
            <input
              value={newLeagueName}
              onChange={(e) => setNewLeagueName(e.target.value)}
              placeholder="e.g. IPL 2026, Big Bash League"
              className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              disabled={createMutation.isPending}
            />
            <button
              onClick={() => createMutation.mutate(newLeagueName)}
              disabled={createMutation.isPending || !newLeagueName.trim()}
              className="px-6 py-2 bg-green-600 text-white font-medium rounded-lg hover:bg-green-700 disabled:bg-gray-400 transition-colors"
            >
              {createMutation.isPending ? 'Creating...' : 'Create League'}
            </button>
          </div>
          {createMutation.error && (
            <p className="text-red-600 mt-2">{String(createMutation.error)}</p>
          )}
        </div>

        {/* Leagues list */}
        <div className="bg-white rounded-xl shadow-lg">
          <div className="p-6 border-b">
            <h2 className="text-2xl font-semibold text-gray-900">
              Leagues ({leaguesQuery.data?.length || 0})
            </h2>
          </div>
          
          {leaguesQuery.isLoading && (
            <div className="p-8 text-center">
              <p className="text-gray-500">Loading leagues...</p>
            </div>
          )}
          
          {leaguesQuery.isError && (
            <div className="p-8 text-center">
              <p className="text-red-600">Error: {String(leaguesQuery.error)}</p>
            </div>
          )}
          
          {leaguesQuery.data?.length === 0 && !leaguesQuery.isLoading && (
            <div className="p-8 text-center">
              <p className="text-gray-500">No leagues yet. Create one above!</p>
            </div>
          )}
          
          <div className="divide-y divide-gray-200">
            {leaguesQuery.data?.map((league: any) => (
              <div key={league.id} className="p-6 hover:bg-gray-50 transition-colors">
                <div className="flex justify-between items-center">
                  <div>
                    <h3 className="text-xl font-semibold text-gray-900">{league.name}</h3>
                    <p className="text-sm text-gray-500">ID: {league.id}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
}
